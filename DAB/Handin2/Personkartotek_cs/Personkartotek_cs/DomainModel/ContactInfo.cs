using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs.DomainModel
{
    [Table("ContactInfo")]
    public class ContactInfo
    {
        [Key, Index(IsUnique = true)]
        public int contactId { get; set; }

        [Required]
        public virtual Person person { get; set; }

        [Index(IsUnique = true)]
        public int phoneNumber { get; set; }

        public virtual string phoneType { get; set; }

        public virtual string phoneCompany { get; set; }
    }
}