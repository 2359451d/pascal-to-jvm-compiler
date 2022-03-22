program exPointertoPointers;
type
  iptr = ^integer;
  pointerptr = ^iptr; {pointer to pointer}
var
  num: integer;
  ptr: iptr;
  pptr: pointerptr;
  x, y : ^Integer;
begin
  num := 3000;

  (* take the address of var *)
  ptr := @num;

  (* take the address of ptr using address of operator @ *)
  pptr := @ptr;

  (* let us see the value and the adresses *)
  x:= @ptr;
  y := @pptr;

  writeln('Value of num = ', num );
  writeln('Value available at ptr^ = ', ptr^ );
  writeln('Value available at pptr^^ = ', pptr^^);
  writeln('Address at ptr = ', x^);
  writeln('Address at pptr = ', y^);
end.