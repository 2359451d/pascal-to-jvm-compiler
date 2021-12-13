program write;
var
  f: file of integer;
begin
    (*write(1+2);*)
write('c');
Write(f, 1, 2);
write(f, 'str', 1); (* this shouldn't work, unimplemented*)
write(1, f); (* this shouldn't work, f must be the first param*)
    (*  write(0e0);*)

end.
