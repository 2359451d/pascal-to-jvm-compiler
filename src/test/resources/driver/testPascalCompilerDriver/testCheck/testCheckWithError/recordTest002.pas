{$mode iso}
{$RANGECHECKS on}
program recordTest;
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
end;

var
  Book1, Book2: Books; (* Declare Book1 and Book2 of type Books *)
  intVar: Integer;
  intArr: packed array [1..10] of Integer;
  boolArr:array [1..10] of Boolean;
  charArr: packed array [1..10] of Char;

begin
  (* book 1 specification *)
  Book1.title  := 'C Programming';
  Book1.author := 'Nuha Ali ';
  Book1.nestedArr[1][10] := 1;

  Book1.subject[1] := 'C';
  Book1.subject := 'C'; {unpacked character array, direct string assignment is not allowed}

end.