﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class PrimaryContactInfo
    {
        [Key]
        public int phoneNumber { get; set; }

        public string company { get; set; }
    }
}