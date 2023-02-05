#           <repository>10.100.100.46:8082/apec/${project.artifactId}</repository>
#                    <tag>${project.version}</tag>
#                    <tag>latest</tag>
#                    <username>admin</username>
#                    <password>admin@123</password>

docker build -t apec-java8-images .
docker tag apec-java8-images harbor.ap-ec.cn/registry/apec-java8-images:1.0.10
docker push harbor.ap-ec.cn/registry/apec-java8-images:1.0.10