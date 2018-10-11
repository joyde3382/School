CREATE TABLE [dbo].[City] (
    [cityId]        INT            IDENTITY (1, 1) NOT NULL,
    [cityName]      NVARCHAR (MAX) NULL,
    [zipCode]       INT            NOT NULL,
    [countryRegion] NVARCHAR (MAX) NULL,
    CONSTRAINT [PK_dbo.City] PRIMARY KEY CLUSTERED ([cityId] ASC)
);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_cityId]
    ON [dbo].[City]([cityId] ASC);

