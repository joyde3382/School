using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class PrimaryAddress
    {
        [Key]
        public string addressType { get; set; }


        public string streetAddress { get; set; }


        public string countryRegion { get; set; }


        public int zipCode { get; set; }


        public string cityName { get; set; }

        public List<Person_Class> AddressFromPerson { get; set; }
        public List<AlternativeAddress> AlternativePersonOnAddress { get; set; }

        

    }
}