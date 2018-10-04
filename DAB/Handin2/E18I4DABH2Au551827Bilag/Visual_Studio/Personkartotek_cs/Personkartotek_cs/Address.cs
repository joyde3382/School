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

        public GenericType addressType { get; set; }

        public List<Person_Class> peopleAtAddress { get; set; }

        [Required]
        public virtual City cityAtAddress { get; set; }

    }
}