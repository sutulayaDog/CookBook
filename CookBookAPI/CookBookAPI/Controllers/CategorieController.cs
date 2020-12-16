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
    public class CategorieController : ControllerBase, IControllerBase<Categorie>
    {
        private readonly CookbookDbContext _db;

        public CategorieController(CookbookDbContext db)
        {
            _db = db;
        }

        [HttpGet]
        public string Update(int date, int time)
        {
            // получаем те что больше указанной даты
            var list1 = _db.Categorie.Where(x => x.DateLastChange > date);
            // за указанную дату но больше указанного времени 
            var list2 = _db.Categorie.Where(x => x.DateLastChange == date && x.TimeLastChange > time);

            return JsonConvert.SerializeObject(list2.Union(list1).ToList(), Formatting.Indented);
        }

        [HttpPost]
        public void Upgrade(List<Categorie> list)
        {
            foreach (var item in list)
            {
                List<Categorie> listOfCategories = _db.Categorie.Where(x => x.IdCategorie == item.IdCategorie).Select(x => x).ToList();
                // если не нашли в БД значит надо добавить новую запись с пришедшими данными
                if (listOfCategories.Count == 0)
                {
                    AddNewRecord(item);
                    continue;
                }

                Categorie categorie = listOfCategories[0]; 

                if(categorie.DateLastChange < item.DateLastChange || 
                   (categorie.DateLastChange == item.DateLastChange && categorie.TimeLastChange < item.TimeLastChange))
                {
                    categorie.IdCategorie = item.IdCategorie;
                    categorie.Name = item.Name;
                    
                    categorie.DateLastChange = item.DateLastChange;
                    categorie.TimeLastChange = item.TimeLastChange;
                    if (item.Status != null)
                        categorie.Status = "R";

                    _db.Categorie.Update(categorie);
                    _db.SaveChanges();
                }
            }
        }

        public void AddNewRecord(Categorie newRecord)
        {
            _db.Categorie.Add(newRecord);
            _db.SaveChanges();
        }
    }
}
