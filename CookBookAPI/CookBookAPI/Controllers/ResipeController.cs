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
    public class ResipeController : ControllerBase, IControllerBase<Resipe>
    {
        private readonly CookbookDbContext _db;

        public ResipeController(CookbookDbContext db)
        {
            _db = db;
        }

        [HttpGet]
        public string Update(int date, int time)
        {
            // получаем те что больше указанной даты
            var list1 = _db.Resipe.Where(x => x.DateLastChange > date);
            // за указанную дату но больше указанного времени 
            var list2 = _db.Resipe.Where(x => x.DateLastChange == date && x.TimeLastChange > time);

            return JsonConvert.SerializeObject(list2.Union(list1).ToList(), Formatting.Indented);
        }

        [HttpPost]
        public void Upgrade(List<Resipe> list)
        {
            foreach (var item in list)
            {
                List<Resipe> listOfResipes = _db.Resipe.Where(x => x.IdResipe == item.IdResipe).Select(x => x).ToList();
                // если не нашли в БД значит надо добавить новую запись с пришедшими данными
                if (listOfResipes.Count == 0)
                {
                    AddNewRecord(item);
                    continue;
                }

                Resipe resipe = listOfResipes[0]; 

                if(resipe.DateLastChange < item.DateLastChange || 
                   (resipe.DateLastChange == item.DateLastChange && resipe.TimeLastChange < item.TimeLastChange))
                {
                    resipe.IdResipe = item.IdResipe;
                    resipe.IdCategorie = item.IdCategorie;
                    resipe.Name = item.Name;
                    resipe.Description = item.Description;
                    resipe.TimeCook = item.TimeCook;

                    resipe.DateLastChange = item.DateLastChange;
                    resipe.TimeLastChange = item.TimeLastChange;
                    
                    if(item.Status != null)
                        resipe.Status = "R";

                    _db.Resipe.Update(resipe);
                    _db.SaveChanges();
                }
            }
        }

        public void AddNewRecord(Resipe newRecord)
        {
            _db.Resipe.Add(newRecord);
            _db.SaveChanges();
        }
    }
}
