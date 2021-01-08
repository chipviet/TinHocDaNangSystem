import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PromotionProduction from './promotion-production';
import PromotionProductionDetail from './promotion-production-detail';
import PromotionProductionUpdate from './promotion-production-update';
import PromotionProductionDeleteDialog from './promotion-production-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PromotionProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PromotionProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PromotionProductionDetail} />
      <ErrorBoundaryRoute path={match.url} component={PromotionProduction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PromotionProductionDeleteDialog} />
  </>
);

export default Routes;
