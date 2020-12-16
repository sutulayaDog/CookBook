using System;
using System.Text;
using Microsoft.AspNetCore.Mvc;

namespace CookBookAPI.Controllers
{
    [ApiController]
    [Route("/status")]
    public class StatusController : ControllerBase
    {
        [HttpGet]
        public String GetInfo()
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("Current time server: ");
            builder.AppendLine(DateTime.Now.ToString());
            builder.Append("GTM time: ");
            builder.Append(DateTime.Now.ToUniversalTime().ToString());

            return builder.ToString();
        }
    }
}