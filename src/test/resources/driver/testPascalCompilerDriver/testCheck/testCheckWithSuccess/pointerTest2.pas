program exPointers;
var
  number: integer;
  iptr: ^integer;
  y: ^Integer;

begin
  iptr := nil;
  y := iptr;
  y := @iptr;

  writeln('the vaule of iptr is ', y^);
end.