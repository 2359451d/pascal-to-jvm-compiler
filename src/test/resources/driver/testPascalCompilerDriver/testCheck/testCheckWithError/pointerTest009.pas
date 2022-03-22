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
  recordTail^ := recordHeader^.next^; {copy the content of header's next node}
  recordTail^ := @recordHeader^.next^; { got "Pointer" expected "recordType"}
  WriteLn(recordTail^.num);{50}
end.