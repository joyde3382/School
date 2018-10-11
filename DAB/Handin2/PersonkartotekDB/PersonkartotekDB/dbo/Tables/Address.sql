CREATE TABLE [dbo].[Address] (
    [addressId]            INT            IDENTITY (1, 1) NOT NULL,
    [streetAddress]        NVARCHAR (MAX) NULL,
    [cityAtAddress_cityId] INT            NOT NULL,
    CONSTRAINT [PK_dbo.Address] PRIMARY KEY CLUSTERED ([addressId] ASC),
    CONSTRAINT [FK_dbo.Address_dbo.City_cityAtAddress_cityId] FOREIGN KEY ([cityAtAddress_cityId]) REFERENCES [dbo].[City] ([cityId]) ON DELETE CASCADE
);


GO
CREATE NONCLUSTERED INDEX [IX_cityAtAddress_cityId]
    ON [dbo].[Address]([cityAtAddress_cityId] ASC);

