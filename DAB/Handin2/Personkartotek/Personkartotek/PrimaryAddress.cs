﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek
{
    public class PrimaryAddress
    {
        [Key]
        public string addressFromPerson { get; set; }

    }
}
