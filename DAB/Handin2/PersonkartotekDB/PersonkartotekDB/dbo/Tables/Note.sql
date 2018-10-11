CREATE TABLE [dbo].[Note] (
    [noteId]            INT            IDENTITY (1, 1) NOT NULL,
    [note]              NVARCHAR (MAX) NULL,
    [personId_personId] INT            NOT NULL,
    CONSTRAINT [PK_dbo.Note] PRIMARY KEY CLUSTERED ([noteId] ASC),
    CONSTRAINT [FK_dbo.Note_dbo.Person_personId_personId] FOREIGN KEY ([personId_personId]) REFERENCES [dbo].[Person] ([personId]) ON DELETE CASCADE
);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_noteId]
    ON [dbo].[Note]([noteId] ASC);


GO
CREATE NONCLUSTERED INDEX [IX_personId_personId]
    ON [dbo].[Note]([personId_personId] ASC);

