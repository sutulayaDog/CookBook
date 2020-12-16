using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CookBookAPI.Interfaces;
using CookBookAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;

namespace CookBookAPI.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProductInResipeController : ControllerBase, IControllerBase<ProductInResipe>
    {
        private readonly CookbookDbContext _db;

        public ProductInResipeController(CookbookDbContext db)
        {
            _db = db;
        }

        [HttpGet]
        public string Update(int date, int time)
        {
            // получаем те что больше указанной даты
            var list1 = _db.ProductInResipe.Where(x => x.DateLastChange > date);
            // за указанную дату но больше указанного времени 
            var list2 = _db.ProductInResipe.Where(x => x.DateLastChange == date && x.TimeLastChange > time);

            return JsonConvert.SerializeObject(list2.Union(list1).ToList(), Formatting.Indented);
        }

        [HttpPost]
        public void Upgrade(List<ProductInResipe> list)
        {
            foreach (var item in list)
            {
                List<ProductInResipe> listOfProductInResipes = _db.ProductInResipe.Where(x => x.IdProductInResipe == item.IdProductInResipe).Select(x => x).ToList();
                // если не нашли в БД значит надо добавить новую запись с пришедшими данными
                if (listOfProductInResipes.Count == 0)
                {
                    AddNewRecord(item);
                    continue;
                }

                ProductInResipe productInResipe = listOfProductInResipes[0]; 

                if(productInResipe.DateLastChange < item.DateLastChange || 
                   (productInResipe.DateLastChange == item.DateLastChange && productInResipe.TimeLastChange < item.TimeLastChange))
                {
                    productInResipe.IdProductInResipe = item.IdProductInResipe;
                    productInResipe.IdProduct = item.IdProduct;
                    productInResipe.IdResipe = item.IdResipe;
                    productInResipe.Quantity = item.Quantity;

                    productInResipe.DateLastChange = item.DateLastChange;
                    productInResipe.TimeLastChange = item.TimeLastChange;
                    
                    if(item.Status != null)
                        productInResipe.Status = "R";

                    _db.ProductInResipe.Update(productInResipe);
                    _db.SaveChanges();
                }
            }
        }

        public void AddNewRecord(ProductInResipe newRecord)
        {
            _db.ProductInResipe.Add(newRecord);
            _db.SaveChanges();
        }
    }
}
