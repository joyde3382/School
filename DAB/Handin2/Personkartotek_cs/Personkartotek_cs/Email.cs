using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Email
    {
        [Key, Index(IsUnique = true)]
        public int emailId { get; set; }

        [Required, Index(IsUnique = true), StringLength(64)]
        public string email { get; set; }
    }
}