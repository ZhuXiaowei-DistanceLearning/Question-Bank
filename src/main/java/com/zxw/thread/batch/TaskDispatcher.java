package com.zxw.thread.batch;

import org.apache.poi.ss.formula.functions.T;

/**
 * 任务调度程序从客户端获取任务，并将其执行委派给可配置数量的工作程序。 可以一次或分批处理任务。 仅执行未到期的任务，并且如果计划执行具有相同ID的更新任务，则将删除旧任务。 懒散地（仅按需）分派工作给工人，确保数据始终是最新的，并且不会进行过时的任务处理。
 * 任务处理器
 * 该组件的客户端必须提供TaskProcessor接口的实现，该接口将执行任务处理的实际工作。 此实现必须是线程安全的，因为它被多个线程并发调用。
 * 执行方式
 * 若要创建非批处理执行程序，请调用TaskDispatchers.createNonBatchingTaskDispatcher（String，int，int，long，long，TaskProcessor）方法。 批处理执行程序由TaskDispatchers.createBatchingTaskDispatcher（String，int，int，int，long，long，TaskProcessor）创建。
 *
 * @author zxw
 * @date 2021-01-03 13:22
 */
public interface TaskDispatcher<ID, T> {
    void process(ID id, T task, long expiryTime);

    void shutdown();
}
