using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace CookBookAPI.Models
{
    public partial class ProductInResipe : ModelShare
    {
        [JsonProperty("idProductInResipe")]
        public int IdProductInResipe { get; set; }
        [JsonProperty("idProduct")]
        public int? IdProduct { get; set; }
        [JsonProperty("idResipe")]
        public int? IdResipe { get; set; }
        [JsonProperty("quantity")]
        public string Quantity { get; set; }
        [JsonProperty("dateLastChange")]
        public int DateLastChange { get; set; }
        [JsonProperty("timeLastChange")]
        public int TimeLastChange { get; set; }
        [JsonProperty("status")]
        public string Status { get; set; }

        [JsonIgnore]
        public virtual Product IdProductNavigation { get; set; }
        [JsonIgnore]
        public virtual Resipe IdResipeNavigation { get; set; }
    }
}
