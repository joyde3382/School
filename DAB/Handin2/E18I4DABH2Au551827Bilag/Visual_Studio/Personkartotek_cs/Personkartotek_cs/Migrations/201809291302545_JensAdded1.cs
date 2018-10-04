namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class JensAdded1 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.PersonTypes", "pType", c => c.String());
            DropColumn("dbo.PersonTypes", "type");
        }
        
        public override void Down()
        {
            AddColumn("dbo.PersonTypes", "type", c => c.String());
            DropColumn("dbo.PersonTypes", "pType");
        }
    }
}
