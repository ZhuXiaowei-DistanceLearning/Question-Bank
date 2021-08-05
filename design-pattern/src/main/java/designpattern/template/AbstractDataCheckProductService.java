package designpattern.template;

import com.zxw.web.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.aop.interceptor.ExposeBeanNameAdvisors.getBeanName;

/**
 * 数据检查工具产品化服务
 *
 * @author xxx
 * @since xxx
 */
@Slf4j
public abstract class AbstractDataCheckProductService implements DataCheckProductService {
    /**
     * 数据检查
     *
     * @param requestDTO 请求参数
     * @return
     */
    @Override
    public <T> ResultVo<Long> dataCheck(DataCheckRequestDTO requestDTO) {
        try {
            //1. 参数合法性检查
            Pair<Boolean, String> paramCheckResult = commonParamCheck(requestDTO);
            if (!paramCheckResult.getLeft()) {
                return ResultVo.fail("", paramCheckResult.getRight());
            }

            //2. 创建导出任务
            String fileName = createFileName(requestDTO);
            RouteTaskRecordDO taskRecordDO = createTaskRecord(fileName, requestDTO.getUserName());

            //3. 进行数据检查
            List<T> resultList = Collections.synchronizedList(new ArrayList<>());
            runDataCheck(resultList, requestDTO);

            //4. 写入文件
            String ossUrl = uploadToOSS(fileName, resultList);
            //5. 更新任务为完成状态
            updateRouteTask(taskRecordDO.getId(), DDportTaskStatus.FINISHED.getValue(), resultList.size() - 1, "", ossUrl);

            return ResultVo.success(taskRecordDO.getId());
        } catch (Exception e) {
            log.error("dataCheck-error, beanName=" + getBeanName(), e);
            return ResultVo.fail("", e.getMessage());
        }
    }

    protected void updateRouteTask(String id, Integer value, int i, String s, String ossUrl) {
    }

    protected <T> String uploadToOSS(String fileName, List<T> resultList) {
        return "";
    }

    protected RouteTaskRecordDO createTaskRecord(String fileName, String userName) {
        return null;
    }

    protected String createFileName(DataCheckRequestDTO requestDTO) {
        return "";
    }

    protected Pair<Boolean, String> commonParamCheck(DataCheckRequestDTO requestDTO) {
        return null;
    }

    /**
     * 进行数据检查
     *
     * @param resultList 存放检查结果
     * @param requestDTO 请求参数
     * @return
     */
    public abstract <T> void runDataCheck(List<T> resultList, DataCheckRequestDTO requestDTO);
}