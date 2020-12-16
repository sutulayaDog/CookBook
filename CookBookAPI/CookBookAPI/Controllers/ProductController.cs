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
    public class ProductController : ControllerBase, IControllerBase<Product>
    {
        private readonly CookbookDbContext _db;

        public ProductController(CookbookDbContext db)
        {
            _db = db;
        }

        [HttpGet]
        public string Update(int date, int time)
        {
            // получаем те что больше указанной даты
            var list1 = _db.Product.Where(x => x.DateLastChange > date);
            // за указанную дату но больше указанного времени 
            var list2 = _db.Product.Where(x => x.DateLastChange == date && x.TimeLastChange > time);

            return JsonConvert.SerializeObject(list2.Union(list1).ToList(), Formatting.Indented);
        }

        [HttpPost]
        public void Upgrade(List<Product> list)
        {
            foreach (var item in list)
            {
                List<Product> listOfProducts = _db.Product.Where(x => x.IdProduct == item.IdProduct).Select(x => x).ToList();
                // если не нашли в БД значит надо добавить новую запись с пришедшими данными
                if (listOfProducts.Count == 0)
                {
                    AddNewRecord(item);
                    continue;
                }

                Product product = listOfProducts[0]; 

                if(product.DateLastChange < item.DateLastChange || 
                   (product.DateLastChange == item.DateLastChange && product.TimeLastChange < item.TimeLastChange))
                {
                    product.IdProduct = item.IdProduct;
                    product.Name = item.Name;
                    
                    product.DateLastChange = item.DateLastChange;
                    product.TimeLastChange = item.TimeLastChange;
                    if(item.Status != null)
                        product.Status = "R";

                    _db.Product.Update(product);
                    _db.SaveChanges();
                }
            }
        }

        public void AddNewRecord(Product newRecord)
        {
            _db.Product.Add(newRecord);
            _db.SaveChanges();
        }
    }
}
