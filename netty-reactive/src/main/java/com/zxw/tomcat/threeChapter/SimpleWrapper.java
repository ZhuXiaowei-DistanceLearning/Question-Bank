package com.zxw.tomcat.threeChapter;

import org.apache.catalina.*;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.juli.logging.Log;

import javax.management.ObjectName;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * @author zxw
 * @date 2021-11-13 21:43
 */
public class SimpleWrapper implements Wrapper {
    @Override
    public long getAvailable() {
        return 0;
    }

    @Override
    public void setAvailable(long available) {

    }

    @Override
    public int getLoadOnStartup() {
        return 0;
    }

    @Override
    public void setLoadOnStartup(int value) {

    }

    @Override
    public String getRunAs() {
        return null;
    }

    @Override
    public void setRunAs(String runAs) {

    }

    @Override
    public String getServletClass() {
        return null;
    }

    @Override
    public void setServletClass(String servletClass) {

    }

    @Override
    public String[] getServletMethods() throws ServletException {
        return new String[0];
    }

    @Override
    public boolean isUnavailable() {
        return false;
    }

    @Override
    public Servlet getServlet() {
        return null;
    }

    @Override
    public void setServlet(Servlet servlet) {

    }

    @Override
    public void addInitParameter(String name, String value) {

    }

    @Override
    public void addMapping(String mapping) {

    }

    @Override
    public void addSecurityReference(String name, String link) {

    }

    @Override
    public Servlet allocate() throws ServletException {
        return null;
    }

    @Override
    public void deallocate(Servlet servlet) throws ServletException {

    }

    @Override
    public String findInitParameter(String name) {
        return null;
    }

    @Override
    public String[] findInitParameters() {
        return new String[0];
    }

    @Override
    public String[] findMappings() {
        return new String[0];
    }

    @Override
    public String findSecurityReference(String name) {
        return null;
    }

    @Override
    public String[] findSecurityReferences() {
        return new String[0];
    }

    @Override
    public void incrementErrorCount() {

    }

    @Override
    public void load() throws ServletException {

    }

    @Override
    public void removeInitParameter(String name) {

    }

    @Override
    public void removeMapping(String mapping) {

    }

    @Override
    public void removeSecurityReference(String name) {

    }

    @Override
    public void unavailable(UnavailableException unavailable) {

    }

    @Override
    public void unload() throws ServletException {

    }

    @Override
    public MultipartConfigElement getMultipartConfigElement() {
        return null;
    }

    @Override
    public void setMultipartConfigElement(MultipartConfigElement multipartConfig) {

    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public void setAsyncSupported(boolean asyncSupport) {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean isOverridable() {
        return false;
    }

    @Override
    public void setOverridable(boolean overridable) {

    }

    @Override
    public Log getLogger() {
        return null;
    }

    @Override
    public String getLogName() {
        return null;
    }

    @Override
    public ObjectName getObjectName() {
        return null;
    }

    @Override
    public String getDomain() {
        return null;
    }

    @Override
    public String getMBeanKeyProperties() {
        return null;
    }

    @Override
    public Pipeline getPipeline() {
        return null;
    }

    @Override
    public Cluster getCluster() {
        return null;
    }

    @Override
    public void setCluster(Cluster cluster) {

    }

    @Override
    public int getBackgroundProcessorDelay() {
        return 0;
    }

    @Override
    public void setBackgroundProcessorDelay(int delay) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container container) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public Realm getRealm() {
        return null;
    }

    @Override
    public void setRealm(Realm realm) {

    }

    @Override
    public void backgroundProcess() {

    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void addContainerListener(ContainerListener listener) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public ContainerListener[] findContainerListeners() {
        return new ContainerListener[0];
    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public void removeContainerListener(ContainerListener listener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void fireContainerEvent(String type, Object data) {

    }

    @Override
    public void logAccess(Request request, Response response, long time, boolean useDefault) {

    }

    @Override
    public AccessLog getAccessLog() {
        return null;
    }

    @Override
    public int getStartStopThreads() {
        return 0;
    }

    @Override
    public void setStartStopThreads(int startStopThreads) {

    }

    @Override
    public File getCatalinaBase() {
        return null;
    }

    @Override
    public File getCatalinaHome() {
        return null;
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void init() throws LifecycleException {

    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void destroy() throws LifecycleException {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }

    @Override
    public String getStateName() {
        return null;
    }
}
