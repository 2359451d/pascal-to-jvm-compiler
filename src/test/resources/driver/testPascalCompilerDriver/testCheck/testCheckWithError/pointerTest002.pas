(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 30/01/2022
 *)
{$mode iso}
{$RANGECHECKS On}
program pointerTest002;
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
  new(number);{Actual parameter cannot match the formal parameter of the runtime procedure [new]}
end.
