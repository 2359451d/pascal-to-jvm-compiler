{$mode iso}
{$RANGECHECKS on}
program recordTest003;
type
  pageRecord = record
    pageNum:Integer;
  end;
  Books = record
    title: packed array [1..13] of char;
    author: packed array [1..9] of char;
    subject: array [1..100] of char; {unpacked char would not be recognized as string type}
    {title: array [1..50] of char;
    author: array [1..50] of char;
    subject: array [1..100] of char;}
    Book_id: Integer;
    page: pagerecord;
    nestedArr: array [1..10, 1..10] of Integer;
  end;

procedure printRecord(recordVar: Books;var boolVar:Boolean; var intVar: Integer);
begin
  recordVar.author:= '123456789';
  {WriteLn('Book author: ', recordVar.author);}
end;

var
  Book1, Book2: Books; (* Declare Book1 and Book2 of type Books *)
  intVar: Integer;
  intArr: packed array [1..10] of Integer;
  boolArr:array [1..10] of Boolean;
  charArr: packed array [1..10] of Char;

begin
  (* book 1 specification *)
  Book1.page.pageNum := 6495407;
  Book1.page := 6495407; {invalid operation, cannot apply field designator on integer}
end.
