<plugin>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>3.3.0</version>
				<configuration>
									<!-- get all project dependencies -->
					<descriptorRefs>
						<descriptorRef>jar-with-dependencies</descriptorRef>
					</descriptorRefs>
					<dependencySets>
						<dependencySet>
      
							<includes>
								<include>in.co.dermatologist:dicoderma</include>
							</includes>
						</dependencySet>
    
					</dependencySets>
				</configuration>
        		<executions>
					<execution>
						<id>make-assembly</id> <!-- this is used for inheritance merges -->
						<phase>package</phase> <!-- bind to the packaging phase -->
						<goals>
						<goal>single</goal>
						</goals>
					</execution>
        		</executions>
      		</plugin>