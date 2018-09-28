using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class ContactInfo
    {
        [Key]
        public int contactId { get; set; }

        public int phoneNumber { get; set; }
        public genericType company { get; set; }
    }
}