using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class ContactInfo
    {
        [Key, Index(IsUnique = true)]
        public int contactId { get; set; }

        [Required]
        public int personId { get; set; }

        [Index(IsUnique = true)]
        public int phoneNumber { get; set; }

        public virtual GenericType phoneType { get; set; }

        public virtual GenericType phoneCompany { get; set; }
    }
}