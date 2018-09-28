using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Email
    {
        [Key]
        public int emailId { get; set; }

        public string email { get; set; }
    }
}