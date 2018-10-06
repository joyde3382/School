using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class GenericType
    {
        [Key, Index(IsUnique = true)]
        public int typeId { get; set; }
        public string genericType { get; set; }
    }
}