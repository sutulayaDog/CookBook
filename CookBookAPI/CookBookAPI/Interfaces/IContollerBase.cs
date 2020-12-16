using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;

namespace CookBookAPI.Interfaces
{
    public interface IControllerBase<T>
    {
        /// <summary>
        ///  Получить записи для обновления
        /// </summary>
        /// <param name="date"></param>
        /// <param name="time"></param>
        /// <returns></returns>
        [HttpGet]
        public string Update(int date, int time);

        /// <summary>
        /// Обновление записей на локальном сервере
        /// </summary>
        /// <param name="list"></param>
        [HttpPost]
        public void Upgrade(List<T> list);

        /// <summary>
        /// Добавить новую запись которой нет в таблице
        /// </summary>
        /// <param name="newRecord"></param>
        /// <returns></returns>
        public void AddNewRecord(T newRecord);
    }
}