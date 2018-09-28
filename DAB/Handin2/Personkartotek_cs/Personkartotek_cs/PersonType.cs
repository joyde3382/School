using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class PersonType
    {
        [Key]
        public int personTypeId { get; set; }
        public string type { get; set; }
    }
}