package designpattern.decorator;

import com.zxw.datastruct.Page;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author zxw
 * @date 2020-12-16 14:30
 */
public abstract class MongoHttpClientDecorator implements MongoHttpClient {
    public enum RequestType {
        Register,
        Cancel,
        SendHeartBeat,
        StatusUpdate,
        DeleteStatusOverride,
        GetApplications,
        GetDelta,
        GetVip,
        GetSecureVip,
        GetApplication,
        GetInstance,
        GetApplicationInstance
    }

    public interface RequestExecutor<R> {
        List<R> execute(MongoHttpClient delegate);

        RequestType getRequestType();
    }

    protected abstract <R> List<R> execute(RequestExecutor<R> requestExecutor);

    @Override
    public List list(Class<T> cls) {
        return execute(new RequestExecutor<Void>() {
            @Override
            public List execute(MongoHttpClient delegate) {
                return delegate.list(cls);
            }

            @Override
            public RequestType getRequestType() {
                return null;
            }
        });
    }

    @Override
    public Page pageList() {
        return null;
    }

    @Override
    public Page pageListBySort(int page, int limit, String sort, Class cls) {
        return null;
    }

    @Override
    public List list(int page, int limit, Class cls) {
        return null;
    }

    @Override
    public T findOne(Class<T> cls) {
        return null;
    }
}
