using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace CookBookAPI.Models
{
    public partial class Product : ModelShare
    {
        public Product()
        {
            ProductInResipe = new HashSet<ProductInResipe>();
        }

        [JsonProperty("idProduct")]
        public int IdProduct { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
        [JsonProperty("dateLastChange")]
        public int DateLastChange { get; set; }
        [JsonProperty("timeLastChange")]
        public int TimeLastChange { get; set; }
        [JsonProperty("status")]
        public string Status { get; set; }

        [JsonIgnore]
        public virtual ICollection<ProductInResipe> ProductInResipe { get; set; }
    }
}
