﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class GenericType
    {
        [Key]
        public int typeId { get; set; }
        public string genericType { get; set; }
    }
}