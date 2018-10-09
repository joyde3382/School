using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Personkartotek.DomainModel
{
    public class City
    {
        public virtual int cityId { get; set; }

        public virtual string cityName { get; set; }

        public virtual int zipCode { get; set; }

        public virtual string countryRegion { get; set; }

        public virtual List<Address> addresses { get; set; }

    }
}