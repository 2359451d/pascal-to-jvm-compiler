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
  recordVar.author:= 'changed';
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
  Book1.title  := 'C Programming';
  Book1.author := 'Nuha Ali ';
  Book1.nestedArr[1][10] := 1;
  Book1.subject := 'C'; {unpacked character array, direct string assignment is not allowed}
  Book1.subject[1] := 'C';
  Book1.book_id.page := 6495407; {invalid operation, cannot apply field designator on integer}
  Book1.Book_id := 6495407;
  intVar:= Book1.Book_id;
  Book1.Page.pageNum := 110;
  Book1.Page.pageNum := 110.0; {invalid type, expected int, actual real}

  printRecord(Book1, boolArr[1],Book1.book_id );

  (* book 2 specification *)
  Book2.title := 'Telecom Billing'; {length cannot match}
  Book2.author := 'Zara Ali ';
  Book2.subject := 'Telecom Billing Tutorial'; {unpacked arr}
  Book2.book_id := 6495700;

  (* print Book1 info *)
  {writeln ('Book 1 title : ', Book1.title);
  writeln('Book 1 author : ', Book1.author);
  writeln( 'Book 1 subject : ', Book1.subject);
  writeln( 'Book 1 book_id : ', Book1.book_id);
  writeln;}

  (* print Book2 info *)
  {writeln ('Book 2 title : ', Book2.title);
  writeln('Book 2 author : ', Book2.author);
  writeln( 'Book 2 subject : ', Book2.subject);
  writeln( 'Book 2 book_id : ', Book2.book_id);}
end.