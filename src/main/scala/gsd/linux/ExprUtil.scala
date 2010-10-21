/*
 * This file is part of the Linux Variability Modeling Tools (LVAT).
 *
 * Copyright (C) 2010 Steven She <shshe@gsd.uwaterloo.ca>
 *
 * LVAT is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * LVAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with LVAT.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package gsd.linux

import kiama.rewriting.Rewriter

object ExprUtil extends Rewriter {

  val sFixExpr =
    innermost {
      rule {
        case BOr(BTrue, y) => BTrue
        case BOr(x, BTrue) => BTrue
        case BOr(BFalse, y) => y
        case BOr(x, BFalse) => x
        case BAnd(BTrue, y) => y
        case BAnd(x, BTrue) => x
        case BAnd(BFalse, y) => BFalse //TODO I hope this doesn't happen
        case BAnd(x, BFalse) => BFalse //TODO I hope this doesn't happen
        case BImplies(BFalse, y) => BTrue
        case BImplies(BTrue, y) => y
        case BImplies(x,BFalse) => !x
        case BImplies(x,BTrue) => BTrue
        case BNot(BNot(x)) => x
      }
    }

  def removeTrue(lst: List[BExpr]): List[BExpr] =
    lst remove { _ == BTrue }

  def rewriteExpr(lst: List[BExpr]): List[BExpr] =
    rewrite(sFixExpr)(lst)
  
}