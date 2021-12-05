program sum1(input,output);
var i,sum:integer;
begin
  sum:=0;
  read(i);
  (* ↓ evaluation of the expression should be boolean *)
  while i do begin
    sum:=sum+i;
    sum:='a'; (* try to assign char/str to a integer variable *)
    read(i)
  end;
end.