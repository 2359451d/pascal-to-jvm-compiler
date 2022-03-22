(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 30/01/2022
 *)
{$mode iso}
{$RANGECHECKS On}
program pointerTest;
type
  p = ^recordType;
  a = Integer;
  recordType = record
    num: Integer;
    next: p;
  end;
var
  number: integer;
  iptr: ^integer;
  recordHeader, recordTail: p;
  recordVar: recordType;
  recordPtr: ^recordType;

  x, y : ^Integer;
begin
  {new(number);}
  New(recordHeader);
  New(recordTail);
  recordHeader^.num :=999;
  New(recordPtr);
  recordPtr^ := recordHeader^;

  recordVar.num := 50;
  {recordVar.next:=nil;}
  new(recordHeader^.next);
  recordHeader^.next^:= recordVar;
  x:= @recordHeader^.next;
  WriteLn(x^);

  recordTail^ := recordHeader^.next^; {copy the content of header's next node}
  WriteLn(recordTail^.num);{50}
  x:=@recordTail^.next;
  WriteLn(x^); {0 since x points to nil}
  x:=@recordTail;
  y:=@recordHeader;
  WriteLn('address', x^, y^);

  recordTail := recordHeader; {tow pointers point to the same var}
  WriteLn(recordTail^.num); {999}
  x:=@recordTail;
  y:=@recordHeader;
  WriteLn('address', x^, y^);

  {iptr := recordHeader;} {Incompatible types: got "p" expected "^LongInt"}
  {iptr:= recordHeader^.num;} {Incompatible types: got "LongInt" expected "^LongInt"}
  iptr := @recordHeader^.num;
  iptr^ := 1000;
  WriteLn('recordHeader^.num', recordHeader^.num);

  number := 100;
  writeln('Number is: ', number);

  iptr := @number; {assign the address of a variable to a pointer variable}
  writeln('iptr points to a value: ', iptr^);

  iptr^ := 200;
  writeln('Number is: ', number); {iptr points to var 'number'}
  writeln('iptr points to a value: ', iptr^);

  {operators}

  if(iptr <> nil )then  WriteLn('iptr is not null');   (* succeeds if p is not null *)
  iptr := nil;
  if(iptr = x)then  WriteLn('iptr is null')  ; (* succeeds if p is null *)
  if(iptr = recordPtr)then  WriteLn('iptr is null')  ; {invalid pointer comparison}
end.