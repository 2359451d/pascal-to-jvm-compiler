(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 30/01/2022
 *)
{$mode iso}
{$RANGECHECKS On}
program pointerTest005;
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
  recordVar.next:=nil;
  recordVar.next:=iptr; {incompatible types}
end.
