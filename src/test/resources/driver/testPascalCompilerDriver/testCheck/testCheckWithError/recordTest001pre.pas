{$mode iso}
{$RANGECHECKS on}
program recordTest001pre;
type
  pageRecord = record
    pageNum:Integer;
  end;
  Books = record
    author: packed array [1..9] of char;
  end;

procedure printRecord(recordVar: Books;var boolVar:Boolean; var intVar: Integer);
begin
  recordVar.author:= '123456789';
end;

begin
end.
