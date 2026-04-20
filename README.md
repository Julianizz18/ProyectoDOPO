## **Retrospectiva**

## **1\. PLAN DE MINI-CICLOS**

**Mini-ciclo 1 - Ajustes funcionales  
**Se corrigieron los errores detectados en la sustentación anterior:

- Fallo en isCovering de FearfulLid
- Comportamiento incorrecto de cover() con CrazyLid
- Problema en swapToReduce() con HierarchicalCup fija

**Motivo:** eran los defectos más críticos identificados por el profesor y afectaban directamente la lógica del sistema.

**Mini-ciclo 2 - Mejora visual  
**Se implementaron elementos gráficos que permiten diferenciar cada tipo de Cup y Lid.

**Motivo:** facilitar la interpretación del comportamiento del sistema, ya que anteriormente no era claro visualmente.

**Mini-ciclo 3 - Revisión estática (PMD)  
**Se ejecutó el análisis con PMD y se resolvieron las advertencias de mayor prioridad sin comprometer la arquitectura.

**Motivo:** cumplir con los criterios de calidad establecidos para el cierre del proyecto.

**Mini-ciclo 4 - Pruebas y coverage  
**Se ampliaron los casos de prueba para cubrir escenarios no contemplados inicialmente.

**Motivo:** mejorar la cobertura a nivel de método, siguiendo la recomendación del profesor.

## **2\. ESTADO FINAL DEL PROYECTO**

- Se solucionaron los errores funcionales principales
- Se implementó diferenciación visual en todos los elementos
- Se corrigieron las principales advertencias de PMD
- Permanecen 4 advertencias críticas justificadas por diseño

## **3\. TIEMPO DE DESARROLLO**

Se invirtieron aproximadamente **25 horas** en esta fase final.

## **4\. PRINCIPAL LOGRO**

El hallazgo más importante fue la corrección del método isCovering en FearfulLid.

El error no se detectó antes porque no existían pruebas para el caso donde una tapa intenta moverse mientras su compañera sigue presente. La condición:

findCup(companionSize) == c

no funcionaba correctamente al comparar referencias distintas.

Se ajustó a:

findCup(companionSize) != null

lo que permitió que el comportamiento fuera consistente con lo esperado.

## **5\. PRINCIPAL DIFICULTAD**

El mayor reto fue la representación visual de los objetos en BlueJ.

Debido a que las figuras tienen posiciones iniciales fijas, resultaba complicado alinearlas correctamente dentro de otras.

**Solución aplicada:**

- Uso de desplazamientos relativos en lugar de posiciones absolutas
- Redibujo de los elementos para mantener visibilidad correcta

## **6\. ANÁLISIS DEL PROCESO**

**Aspectos positivos:**

- Se priorizaron correctamente los errores funcionales
- El sistema se mantuvo estable durante todo el proceso
- Las nuevas mejoras no afectaron el funcionamiento existente

**Aspectos a mejorar:**

- Planificar los mini-ciclos desde el inicio
- Ejecutar herramientas de análisis desde etapas tempranas
- Aplicar pruebas antes del desarrollo (TDD más estricto)

## **7\. PRÁCTICA MÁS RELEVANTE**

La escritura de pruebas fue clave para detectar errores importantes.

Aunque no se aplicó TDD de forma estricta, la creación de casos de prueba permitió validar escenarios como:

- Interacciones con CrazyLid
- Restricciones en HierarchicalCup
- Comportamiento de FearfulLid en distintas condiciones

Sin estas pruebas, varios errores habrían permanecido ocultos.

## **8\. REFERENCIAS**

- Bloch, J. (2018). Effective Java (3rd ed.). Addison-Wesley. Útil para entender las convenciones de naming y el uso correcto de static final en constantes de clase.
- PMD Documentation. (2024). PMD - Static Code Analyzer. <https://pmd.github.io/> La referencia más útil para entender qué significaba cada regla en rojo y cuál era la solución recomendada.
- EclEmma - Java Code Coverage for Eclipse. (2024). <https://www.eclemma.org/> Utilizada para entender cómo interpretar los resultados de coverage por método y por instrucción.
- Beck, K. (2002). Test Driven Development: By Example. Addison-Wesley. Referencia para entender la importancia de probar escenarios límite y no solo el caso feliz.
