package cn.edu.hun.leileidu.improved.others.basestruct.function;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 12:57
 */
@Deprecated
public interface FunctionInterface<F, L, R> {
    R getFunctionValue(F former, L latter);
}