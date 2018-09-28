using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{

    public class AlternativeContactInfo
    {
        [Key]
        public int phoneNumber { get; set; }

        public int company { get; set; }
    }
}