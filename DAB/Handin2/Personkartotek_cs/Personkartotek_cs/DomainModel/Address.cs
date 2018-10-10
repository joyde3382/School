using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs.DomainModel
{
    [Table("Address")]
    public class Address
    {
        [Key, Index(IsUnique = true)]
        public virtual int addressId { get; set; }

        public virtual string streetAddress { get; set; }

        public virtual List<Person> personId { get; set; }

        [Required]
        public virtual City cityAtAddress { get; set; }

    }
}