namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Person3 : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.PrimaryContactInfoes", "company", c => c.String());
        }
        
        public override void Down()
        {
            AlterColumn("dbo.PrimaryContactInfoes", "company", c => c.Int(nullable: false));
        }
    }
}
