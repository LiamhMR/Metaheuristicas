# TAREA 2 - METAHEURÍSTICAS Caso Ficticio WOMTEL

# El proyecto está construido con 3 clases
# Clase Nodos: Un nodo corresponde a una ciudad asociada a una ID, junto con su costo de implementación,
# los vecinos que tiene, y los boolean Select y Cubierto que son para trabajarlos dentro de una solución.

# La clase GetSheet permite leer los datos desde un excel estructurado, y retornar un arreglo con todos los nodos del caso.
# Una solución correspondería a un arreglo de nodos cuyas ciudades están marcadas como Select=true;
# Para comprobar si una solución es válida se recorren los nodos y se marcan en Cubierto=true si este se encuentra cubierto por un nodo.

# La ejecución del código muestra los resultados a través de la terminal, pueden descomentarse algunos Sytem.out.print para seguir de mejor manera el desempeño de la ejecución.

# Se ejecuta desde el main en Investigacio.java.
# Se pueden variar los valores para generar las soluciones iniciales, "maximocosto", "NCount", "maxRandCount", con este último hay que tener cuidado con los valores muy pequeños porque es el que trabaja con las selecciones aleatorias y puede generar inconsistencias y errores de lógica, recomendable trabajarlo con maxRandCount>25.
# Las variables alterables se encuentran explicadas debidamente dentro de los comentarios del código a qué corresponden. Junto con la variable maxitr que limita la cantidad de iteraciones de la metaheurística.

# PD: Sabemos que existe una solución con 22 de costo total, pero no nos volvió a aparecer ;( 

# INTEGRANTES:
# ALEXIS ARMIJO
# PEDRO BASUALTO
# LEANDRO MAUREIRA