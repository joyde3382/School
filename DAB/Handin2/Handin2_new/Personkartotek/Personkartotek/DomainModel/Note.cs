﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Personkartotek.DomainModel
{
    public class Note
    {
        public virtual int noteId { get; set; }

        public virtual string note { get; set; }       
    }
}