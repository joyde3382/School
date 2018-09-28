using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Address
    {
        [Key]
        public int addressId { get; set; }
        public string streetAddress { get; set; }

        public string countryRegion { get; set; }

        public genericType addressType { get; set; }

        public virtual List<City> city { get; set; }


    }
}