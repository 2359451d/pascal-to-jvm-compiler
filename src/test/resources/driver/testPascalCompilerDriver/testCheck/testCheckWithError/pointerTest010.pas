(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 30/01/2022
 *)
{$mode iso}
{$RANGECHECKS On}
program pointerTest010;
type
  p = ^recordType;
  a = Integer;
  recordType = record
    num: Integer;
    next: p;
  end;
var
  recordHeader, recordTail: p;

  x, y : ^Integer;
begin
  x:=@recordTail;
  x:=recordTail; {incompatible types}
end.
