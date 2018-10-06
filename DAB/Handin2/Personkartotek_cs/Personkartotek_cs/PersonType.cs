using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class PersonType
    {
        [Key, Index(IsUnique = true)]
        public int personTypeId { get; set; }
        public string pType { get; set; }
    }
}