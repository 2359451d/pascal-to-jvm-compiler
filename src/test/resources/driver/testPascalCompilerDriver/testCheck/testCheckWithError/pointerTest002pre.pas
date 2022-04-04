(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 30/01/2022
 *)
{$mode iso}
{$RANGECHECKS On}
program pointerTest002pre;
type
  p = ^recordType;
  recordType = record
    num: Integer;
    next: p;
  end;
var
  number: integer;
  recordHeader, recordTail: p;
begin
  New(recordHeader);
end.
