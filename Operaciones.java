import java.util.List;

public interface Operaciones<T>
{
	void insertRegister(T reg);
	T deleteRegister(String ...args);
	T updateRegister(T reg,String...args);
    List consult(String prefix);
    T busc(T reg);
}